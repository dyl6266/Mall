/**
 * 자료형에 상관없이 값이 비어있는지 확인한다.
 * 
 * @param value - 타겟 밸류
 * @returns true or false
 */
function isEmpty(value) {
	if (value == null || value == "" || value == undefined || value.length == 0 || (value != null && typeof value == "object" && !Object.keys(value).length)) {
		return true;
	}

	return false;
}

/**
 * 문자열의 마지막 문자의 종성을 반환한다.
 * 
 * @param str - 타겟 문자열
 * @returns 문자열의 마지막 문자의 종성
 */
function charToUnicode(str) {
	return (str.charCodeAt(str.length - 1) - 0xAC00) % 28;
}

/**
 * 필드1, 필드2의 값이 다르면 해당 필드2로 focus한 다음, 메시지 출력
 * 
 * @param field1 - 타겟 필드1
 * @param field2 - 타겟 필드2
 * @param fieldName - 필드 이름
 * @returns 메시지
 */
function equals(field1, field2, fieldName) {
	if (field1.value == field2.value) {
		return true;
	} else {
		if (isEmpty(fieldName) == false) {
			/* alert 메시지 */
			var message = "";
			/* 종성으로 을 / 를 구분 */
			if (charToUnicode(fieldName) > 0) {
				message = fieldName + "이 서로 일치하지 않습니다.";
			} else {
				message = fieldName + "가 서로 일치하지 않습니다.";
			}
			field2.focus();
			alert(message);
			// Swal.fire(message);
		}

		return false;
	}
}

/**
 * field의 값이 올바른 형식인지 체크 (정규표현식 사용)
 * 
 * @param field - 타겟 필드
 * @param fieldName - 필드 이름 (null 허용)
 * @param focusField - 포커스할 필드 (null 허용)
 * @param type - 유형 구분 (이메일, 전화번호 등 / null 허용)
 * @returns 메시지
 */
function isValid(field, fieldName, focusField, type) {
	/* type에 해당하는 정규식 */
	var regExp = "";
	/* alert 메시지 */
	var message = "";

	/* 일반 필드의 경우 */
	if (isEmpty(type)) {
		if (isEmpty(field.value)) {
			/* 종성으로 을 / 를 구분 */
			if (charToUnicode(fieldName) > 0) {
				message = fieldName + "을 확인해 주세요.";
			} else {
				message = fieldName + "를 확인해 주세요.";
			}
			field.focus();
			alert(message);
			return false;
		}
	} else {
		/* 타입을 지정해야 하는 경우 */
		switch (type) {
		case "username":
			regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
			message = "아이디를 이메일 형식으로 입력해 주세요.";
			break;

		case "password":
			regExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{6,20}/;
			message = "올바르지 않은 형식의 비밀번호입니다.";
			break;

		case "phone":
			regExp = /^\d{3}\d{3,4}\d{4}$/;
			message = "올바르지 않은 형식의 연락처입니다.";
			break;

		default:
			break;
		}
		// end of switch

		if (regExp.test(field.value) == false) {
			if (isEmpty($(focusField))) {
				field.focus();
			} else {
				focusField.focus();
			}
			alert(message);
			return false;
		}
	}
	// end of else

	return true;
}

/**
 * 둘 중 비어있지 않은 value를 반환한다.
 * 
 * @param value1 - 타겟 밸류1
 * @param value2 - 타겟 밸류2
 * @returns 비어있지 않은 value
 */
function nvl(value1, value2) {
	return (isEmpty(value1) == false ? value1 : value2);
}

/**
 * 입력받은 숫자에 콤마를 포함해 반환한다.
 * 
 * @param obj
 * @returns 콤마가 붙은 숫자
 */
function makeCommas(target) {
	var regexp = /\B(?=(\d{3})+(?!\d))/g;
	target.value = target.value.replace(regexp, ',');
}

/**
 * Form에 존재하는 input, textarea를 포함한 모든 입력 필드의 name, value를 오브젝트에 key, value 형태로 담아서 반환
 * Ajax를 사용할 때, 파라미터를 하나씩 추가하지 않고, form을 인자로 넘겨서 사용
 * 
 * @param form - Form 객체
 * @returns
 */
function makeAjaxRequestData(form) {

	/* 필드의 name, value를 key, value 형태로 담는 오브젝트 */
	var obj = new Object();
	/* Form 데이터를 배열 형태로 serialize */
	var datas = $(form).serializeArray();
	/* 요소 개수만큼 params에 key, value 형태로 저장 */
	$(datas).each(function(idx, elem) {
		if (isEmpty(elem.value) == false) {
			obj[elem.name] = elem.value;
		}
	});
//	data.forEach(function(value, key) {
//		if (isEmpty(value) == false) {
//			obj[key] = value;
//		}
//	});

	/* spring security csrf 토큰 제거 => header에 정보를 담아서 전송하기 때문에 문제가 되지 않음 */
	delete obj._csrf;
	return obj;
}

/**
 * PushState를 지원하는 브라우저인지 확인
 * 
 * @returns boolean
 */
function isPushStateAvailable() {
	return (typeof(history.pushState) == "function");
}
