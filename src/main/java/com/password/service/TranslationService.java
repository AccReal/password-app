package com.password.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TranslationService {

    private final Map<String, Map<String, String>> translations = new HashMap<>();

    public TranslationService() {
        initTranslations();
    }

    private void initTranslations() {
        // Русский
        Map<String, String> ru = new HashMap<>();
        ru.put("crackTime.instant", "Мгновенно");
        ru.put("crackTime.lessThanSecond", "Меньше секунды");
        ru.put("crackTime.seconds", "секунд");
        ru.put("crackTime.minutes", "минут");
        ru.put("crackTime.hours", "часов");
        ru.put("crackTime.days", "дней");
        ru.put("crackTime.months", "месяцев");
        ru.put("crackTime.years", "лет");
        ru.put("crackTime.thousands", "тысяч лет");
        ru.put("crackTime.millions", "миллионов лет");
        ru.put("crackTime.billions", "миллиардов лет");
        
        ru.put("strength.veryWeak", "Очень слабый");
        ru.put("strength.weak", "Слабый");
        ru.put("strength.medium", "Средний");
        ru.put("strength.strong", "Сильный");
        ru.put("strength.veryStrong", "Очень сильный");
        
        ru.put("recommendation.increaseLength8", "Увеличьте длину пароля минимум до 8 символов");
        ru.put("recommendation.increaseLength12", "Рекомендуется использовать пароль длиной не менее 12 символов");
        ru.put("recommendation.increaseLength16", "Для максимальной безопасности используйте пароль длиной 16+ символов");
        ru.put("recommendation.addLowercase", "Добавьте строчные буквы (a-z)");
        ru.put("recommendation.addUppercase", "Добавьте заглавные буквы (A-Z)");
        ru.put("recommendation.addDigits", "Добавьте цифры (0-9)");
        ru.put("recommendation.addSpecial", "Добавьте специальные символы (!@#$%^&* и т.д.)");
        ru.put("recommendation.avoidSequential", "Избегайте последовательных символов (abc, 123, xyz)");
        ru.put("recommendation.avoidRepeated", "Избегайте повторяющихся символов (aaa, 111)");
        ru.put("recommendation.avoidCommon", "Не используйте распространенные слова и паттерны (password, qwerty, 12345)");
        ru.put("recommendation.excellent", "Отличный пароль! Продолжайте использовать уникальные и сложные пароли");
        ru.put("recommendation.empty", "Пароль не может быть пустым");
        
        ru.put("compare.stronger1", "Первый пароль сильнее на %d баллов. Рекомендуем использовать его.");
        ru.put("compare.stronger2", "Второй пароль сильнее на %d баллов. Рекомендуем использовать его.");
        ru.put("compare.equal", "Оба пароля имеют одинаковую надежность. Можете использовать любой.");
        
        translations.put("ru", ru);

        // Английский
        Map<String, String> en = new HashMap<>();
        en.put("crackTime.instant", "Instantly");
        en.put("crackTime.lessThanSecond", "Less than a second");
        en.put("crackTime.seconds", "seconds");
        en.put("crackTime.minutes", "minutes");
        en.put("crackTime.hours", "hours");
        en.put("crackTime.days", "days");
        en.put("crackTime.months", "months");
        en.put("crackTime.years", "years");
        en.put("crackTime.thousands", "thousand years");
        en.put("crackTime.millions", "million years");
        en.put("crackTime.billions", "billion years");
        
        en.put("strength.veryWeak", "Very Weak");
        en.put("strength.weak", "Weak");
        en.put("strength.medium", "Medium");
        en.put("strength.strong", "Strong");
        en.put("strength.veryStrong", "Very Strong");
        
        en.put("recommendation.increaseLength8", "Increase password length to at least 8 characters");
        en.put("recommendation.increaseLength12", "Recommended to use a password of at least 12 characters");
        en.put("recommendation.increaseLength16", "For maximum security, use a password of 16+ characters");
        en.put("recommendation.addLowercase", "Add lowercase letters (a-z)");
        en.put("recommendation.addUppercase", "Add uppercase letters (A-Z)");
        en.put("recommendation.addDigits", "Add digits (0-9)");
        en.put("recommendation.addSpecial", "Add special characters (!@#$%^&* etc.)");
        en.put("recommendation.avoidSequential", "Avoid sequential characters (abc, 123, xyz)");
        en.put("recommendation.avoidRepeated", "Avoid repeated characters (aaa, 111)");
        en.put("recommendation.avoidCommon", "Don't use common words and patterns (password, qwerty, 12345)");
        en.put("recommendation.excellent", "Excellent password! Keep using unique and complex passwords");
        en.put("recommendation.empty", "Password cannot be empty");
        
        en.put("compare.stronger1", "First password is stronger by %d points. We recommend using it.");
        en.put("compare.stronger2", "Second password is stronger by %d points. We recommend using it.");
        en.put("compare.equal", "Both passwords have equal strength. You can use either one.");
        
        translations.put("en", en);
    }

    public String translate(String key, String lang) {
        Map<String, String> langMap = translations.getOrDefault(lang, translations.get("ru"));
        return langMap.getOrDefault(key, key);
    }

    public String translate(String key, String lang, Object... args) {
        String template = translate(key, lang);
        return String.format(template, args);
    }
}
