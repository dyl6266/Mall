//package com.dy.entity;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//import com.dy.common.Const.Status;
//import com.dy.common.Const.YesNo;
//import com.dy.domain.AttachDTO;
//import com.dy.domain.StockDTO;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity // 해당 클래스가 JPA의 Entity임을 의미, Entity 클래스는 테이블과 매핑됨
//@Table(name = "tb_goods") // goods 테이블과 매핑되도록 지정
//@NoArgsConstructor
//@Getter
//@Setter
//public class GoodsEntity {
//
//	@Id // Entity의 기본키(PK)임을 의미
//	@Column(name = "idx")
//	@GeneratedValue(strategy = GenerationType.AUTO) // DB에서 제공하는 기본키 생성 전략을 따르게 지정
//	private Integer idx;
//
//	@Column(name = "code", length = 11, nullable = false, unique = true)
//	private String code;
//
//	@Column(name = "name", length = 100, nullable = false)
//	private String name;
//
//	@Column(name = "description", length = 2000, nullable = false)
//	private String description;
//
//	@Column(name = "price", nullable = false)
//	private int price;
//
//	@Column(name = "status", length = 1, nullable = false)
//	@Enumerated(EnumType.STRING)
//	private Status status;
//
//	@Column(name = "delete_yn", length = 1, nullable = false)
//	@Enumerated(EnumType.STRING)
//	private YesNo deleteYn;
//
//	@Column(name = "insert_time", nullable = false)
//	private LocalDateTime insertTime = LocalDateTime.now();
//
//	@Column(name = "update_time")
//	private LocalDateTime updateTime;
//
////	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
////	@JoinColumn(name = "code")
////	private StockDTO stock;
////
////	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
////	@JoinColumn(name = "code")
////	private List<AttachDTO> attachList;
//}
