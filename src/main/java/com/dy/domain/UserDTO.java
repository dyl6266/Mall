package com.dy.domain;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO extends CommonDTO implements UserDetails {

	private static final long serialVersionUID = 1L;

	public UserDTO(String username, String password, String nickname, String phone) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.phone = phone;
	}

	/** 번호 (PK) */
	private Integer idx;

	/** 이메일 (UK) */
	@Pattern(message = "아이디를 이메일 형식으로 입력해 주세요.", regexp = "^[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[@]{1}[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[.]{1}[A-Za-z]{1,5}$")
	private String username;

	/** 비밀번호 */
	@Pattern(message = "비밀번호를 올바른 형식으로 입력해 주세요.", regexp = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~`!@#$%\\^&*()-]).{8,20}$")
	private String password;

	/** 별명 */
	@Pattern(message = "닉네임을 올바른 형식으로 입력해 주세요.", regexp = "^[가-힣]{2,10}$")
	private String nickname;

	/** 연락처 */
	@Pattern(message = "연락처를 올바른 형식으로 입력해 주세요.", regexp = "(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})")
	private String phone;

	/** 계정 만료 여부 */
	private boolean accountNonExpired;

	/** 계정 잠김 여부 */
	private boolean accountNonLocked;

	/** 비밀번호 만료 여부 */
	private boolean credentialsNonExpired;

	/** 계정 활성화 여부 */
	private boolean enabled;

	/** 로그인 실패 횟수 */
	private int failureCount;

	/** 마지막 로그인 시간 */
	private Date lastLoginTime;

	/** 권한 리스트 */
	private Collection<? extends GrantedAuthority> authorities;

	/** 권한 이름 리스트 */
	private List<String> authorityNames;

}
