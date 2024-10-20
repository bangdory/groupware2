async function jwtPostProcessing(redirectUrl) {
    try {
        // localStorage에서 JWT 토큰을 가져옴
        const accessToken = localStorage.getItem('accessToken');

        if (!accessToken) {
            throw new Error("JWT 토큰이 없습니다. 로그인이 필요합니다.");
            window.location.href = "/login";
        }

        const response = await fetch(redirectUrl, {
            method: "POST", // 또는 필요에 따라 POST 등으로 변경
            headers: {
                "Authorization": `Bearer ${accessToken}`,
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            // 특정 URL로 이동
            window.location.href = redirectUrl;

        } else if (response.status === 403) {
            // 인증 실패 처리 (예: JWT 만료)
            console.error("인증 실패: 토큰이 만료되었거나 유효하지 않습니다.");
            window.location.href = "/login"; // 다시 로그인 페이지로 리다이렉트
        } else {
            // 기타 오류 처리
            console.error("요청 실패:", response.status);
        }
    } catch (error) {
        console.error("서버 요청 실패:", error);
        window.location.href = "/login"; // 오류 발생 시 로그인 페이지로 이동
    }
}

async function jwtGetProcessing(redirectUrl) {
    try {
        // localStorage에서 JWT 토큰을 가져옴
        const accessToken = localStorage.getItem('accessToken');

        if (!accessToken) {
            throw new Error("JWT 토큰이 없습니다. 로그인이 필요합니다.");
        }

        const response = await fetch(redirectUrl, {
            method: "GET", // 또는 필요에 따라 POST 등으로 변경
            headers: {
                "Authorization": `Bearer ${accessToken}`,
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            // 특정 URL로 이동
            window.location.href = redirectUrl;

        } else if (response.status === 403) {
            // 인증 실패 처리 (예: JWT 만료)
            console.error("인증 실패: 토큰이 만료되었거나 유효하지 않습니다.");
            window.location.href = "/login"; // 다시 로그인 페이지로 리다이렉트
        } else {
            // 기타 오류 처리
            console.error("요청 실패:", response.status);
        }
    } catch (error) {
        console.error("서버 요청 실패:", error);
        window.location.href = "/login"; // 오류 발생 시 로그인 페이지로 이동
    }
}
