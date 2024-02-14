package shootingstar.stellaide.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shootingstar.stellaide.exception.CustomException;
import shootingstar.stellaide.exception.ErrorCode;
import shootingstar.stellaide.repository.user.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CheckDuplicateService {
    private final UserRepository userRepository;

    private static final List<String> forbiddenWords = Arrays.asList("admin", "adm1n", "moderator", "banned");

    public void checkDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    public void checkDuplicateNickname(String nickname) {
        checkForbiddenNickname(nickname);
        if (userRepository.existsByNickname(nickname)) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }
    }

    private void checkForbiddenNickname(String nickname) {
        // 영어(소문자) 와 숫자로만 이루어졌는지
        String regex = "^(?=.*[a-z])(?=.*\\d)[a-z\\d]+$";

        boolean matches = Pattern.matches(regex, nickname);
        if (!matches) {
            throw new CustomException(ErrorCode.INCORRECT_FORMAT_NICKNAME);
        }

        // 정규 표현식과 매치되는지 확인, 금지어 사용 확인
        boolean forbidden = forbiddenWords.stream().noneMatch(nickname::contains);
        if (!forbidden) {
            throw new CustomException(ErrorCode.FORBIDDEN_NICKNAME);
        }
    }
}