document.addEventListener('DOMContentLoaded', function () {

    // localStorage 에서 jwt 가져오기
    const jwtToken = localStorage.getItem("accessToken");

    if (jwtToken) {
        console.log('jwtToken: ' + jwtToken);

        // jwt 토큰을 decoding 해서 json 타입으로 리턴 받기
        const tokenData = decodingJwt(jwtToken);

        if (tokenData) {

            const empNo = tokenData.sub;
            const auth = tokenData.auth;
            console.log('empNo: ' + empNo);
            console.log('auth: ' + auth)

            checkAdmin(auth);


        } else {
            console.error("JWT 토큰에서 empNo 데이터를 찾을 수 없습니다.");
        }

    } else {
        console.error("JWT 토큰을 찾을 수 없습니다.");
    }
});

function decodingJwt(token) {
    try {
        // 토큰의 페이로드 부분(Base64로 인코딩된 부분)을 디코딩
        const base64Url = token.split('.')[1]; // JWT의 두 번째 부분이 페이로드
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));

        return JSON.parse(jsonPayload); // JSON 형식으로 변환하여 반환
    } catch (e) {
        console.error('JWT 파싱 에러:', e);
        return null; // 에러 발생 시 null 반환
    }
}

function checkAdmin(auth) {
    const adminMenu = document.getElementById('adminMenu');
    if (auth === 'ADMIN') {
        adminMenu.style.display = 'block';
    } else {
        adminMenu.style.display = 'none';
    }
}