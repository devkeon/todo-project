package oop.paradigm.todo.member.util;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import oop.paradigm.todo.member.entity.JwtMemberDetail;
import oop.paradigm.todo.member.entity.Member;
import oop.paradigm.todo.member.repository.MemberRepository;

@Slf4j
@Component
public class JwtTokenProvider {

	private final SecretKey key;
	private final Long accessExpiration;
	private final Long refreshExpiration;
	private final MemberRepository memberRepository;

	private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
	private static final String GRANT_TYPE = "Bearer ";

	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, @Value("${jwt.access.expiration}") Long accessExpiration,
		@Value("${jwt.refresh.expiration}") Long refreshExpiration, MemberRepository repository) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.accessExpiration = accessExpiration;
		this.refreshExpiration = refreshExpiration;
		this.memberRepository = repository;
	}

	public String generateAccessToken(Authentication authentication) {

		String authority = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		JwtMemberDetail jwtMemberDetail = (JwtMemberDetail) authentication.getPrincipal();

		return GRANT_TYPE + Jwts.builder()
			.subject(authentication.getName())
			.claim("auth", authority)
			.claim("id", jwtMemberDetail.getUserId())
			.signWith(key)
			.expiration(Date.from(Instant.now().plusMillis(accessExpiration)))
			.compact();
	}

	public String generateRefreshToken() {

		return Jwts.builder()
			.subject(REFRESH_TOKEN_SUBJECT)
			.expiration(Date.from(Instant.now().plusMillis(refreshExpiration)))
			.signWith(key)
			.compact();
	}

	public Authentication getAuthentication(String accessToken) {

		Optional<Claims> payload = Optional.of(Jwts.parser()
			.verifyWith(key)
			.build()
			.parseSignedClaims(accessToken)
			.getPayload());

		Claims claims = payload.stream()
			.filter(claim -> claim.get("auth") != null)
			.findAny()
			.orElseThrow(() -> new JwtException("error occur from auth"));

		Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
			.map(SimpleGrantedAuthority::new)
			.toList();

		Member sessionMember = memberRepository.findById(Long.parseLong(claims.get("id").toString())).stream()
			.findAny()
			.orElseThrow();

		JwtMemberDetail jwtMemberDetail = JwtMemberDetail.jwtMemberBuilder()
			.authorities(authorities)
			.password(sessionMember.getPassword())
			.username(sessionMember.getUserName())
			.userId(sessionMember.getId())
			.build();

		return new UsernamePasswordAuthenticationToken(jwtMemberDetail, sessionMember.getPassword(), authorities);
	}

	public Authentication createAuthentication(Member member) {

		List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(member.getRole().getRole()));

		JwtMemberDetail jwtMemberDetail = JwtMemberDetail.jwtMemberBuilder()
			.userId(member.getId())
			.username(member.getUserName())
			.password(member.getPassword())
			.authorities(authorities)
			.build();

		return new UsernamePasswordAuthenticationToken(jwtMemberDetail, member.getPassword(), authorities);

	}

	public Optional<String> extractAccessToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader("Authorization"))
			.filter(token ->
				token.startsWith(GRANT_TYPE))
			.map(token ->
				token.replace(GRANT_TYPE, ""));
	}

	// Refresh Token 쿠키에서 추출 메서드
	public Optional<String> extractRefreshToken(HttpServletRequest request) {
		return Arrays.stream(request.getCookies())
			.filter(cookie ->
				cookie.getName().equals("refreshToken"))
			.findFirst()
			.map(Cookie::getValue);
	}

	public boolean validate(String token) {

		try {
			Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return false;

	}

}
