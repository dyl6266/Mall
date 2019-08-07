package com.dy.domain;

import java.util.Collection;

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
	private String username;

	/** 비밀번호 */
	private String password;

	/** 별명 */
	private String nickname;

	/** 연락처 */
	private String phone;

	/** 계정 만료 여부 */
	private boolean accountNonExpired = true;

	/** 계정 잠금 여부 */
	private boolean accountNonLocked = true;

	/** 비밀번호 만료 여부 */
	private boolean credentialsNonExpired = true;

	/** 계정 활성화 여부 */
	private boolean enabled = true;

	/** 권한 리스트 */
	private Collection<? extends GrantedAuthority> authorities;

}
