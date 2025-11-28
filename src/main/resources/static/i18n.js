const translations = {
    ru: {
        title: "Генератор и Валидатор Паролей",
        subtitle: "Создавайте надежные пароли и проверяйте их безопасность",
        themeText: "Темная тема",
        lightTheme: "Светлая тема",
        generator: "Генератор Паролей",
        validator: "Валидатор Паролей",
        compare: "Сравнение Паролей",
        statistics: "Статистика",
        length: "Длина пароля:",
        charTypes: "Типы символов:",
        lowercase: "Строчные (a-z)",
        uppercase: "Заглавные (A-Z)",
        digits: "Цифры (0-9)",
        special: "Спецсимволы (!@#$)",
        cyrillic: "Кириллица (а-я)",
        excludeSimilar: "Исключить похожие (0/O, 1/l/I)",
        excludeChars: "Исключить символы:",
        generate: "Сгенерировать Пароль",
        copy: "Копировать",
        validate: "Проверить Пароль",
        enterPassword: "Введите пароль для проверки:",
        enterPassword1: "Первый пароль:",
        enterPassword2: "Второй пароль:",
        compareBtn: "Сравнить",
        crackTime: "Время взлома",
        details: "Детали проверки:",
        recommendations: "Рекомендации:",
        sequential: "Последовательные символы:",
        repeated: "Повторяющиеся символы:",
        commonPatterns: "Общие паттерны:",
        lengthScore: "Баллы за длину:",
        diversityScore: "Баллы за разнообразие:",
        found: "Обнаружены",
        notFound: "Не обнаружены",
        generated: "Сгенерировано паролей:",
        validated: "Проверено паролей:",
        avgScore: "Средняя надежность:",
        activity: "График активности",
        winner: "Победитель:",
        winnerLabel: "Победитель",
        password1Label: "Пароль 1",
        password2Label: "Пароль 2",
        difference: "Разница:",
        recommendation: "Рекомендация:",
        refresh: "Обновить",
        example: "Например:",
        batch: "Массовая генерация",
        batchCount: "Количество паролей:",
        generateBatch: "Сгенерировать пакет",
        downloadCsv: "Скачать CSV",
        copyAll: "Копировать все",
        breachCheck: "Проверка утечек:",
        breachFound: "⚠️ Пароль найден в утечках данных!",
        breachNotFound: "✅ Пароль не найден в утечках",
        breachNote: "Проверка выполняется с использованием Have I Been Pwned API"
    },
    en: {
        title: "Password Generator and Validator",
        subtitle: "Create strong passwords and check their security",
        themeText: "Dark Theme",
        lightTheme: "Light Theme",
        generator: "Password Generator",
        validator: "Password Validator",
        compare: "Password Comparison",
        statistics: "Statistics",
        length: "Password length:",
        charTypes: "Character types:",
        lowercase: "Lowercase (a-z)",
        uppercase: "Uppercase (A-Z)",
        digits: "Digits (0-9)",
        special: "Special (!@#$)",
        cyrillic: "Cyrillic (а-я)",
        excludeSimilar: "Exclude similar (0/O, 1/l/I)",
        excludeChars: "Exclude characters:",
        generate: "Generate Password",
        copy: "Copy",
        validate: "Validate Password",
        enterPassword: "Enter password to check:",
        enterPassword1: "First password:",
        enterPassword2: "Second password:",
        compareBtn: "Compare",
        crackTime: "Crack Time",
        details: "Check Details:",
        recommendations: "Recommendations:",
        sequential: "Sequential characters:",
        repeated: "Repeated characters:",
        commonPatterns: "Common patterns:",
        lengthScore: "Length score:",
        diversityScore: "Diversity score:",
        found: "Found",
        notFound: "Not found",
        generated: "Passwords generated:",
        validated: "Passwords validated:",
        avgScore: "Average strength:",
        activity: "Activity Chart",
        winner: "Winner:",
        winnerLabel: "Winner",
        password1Label: "Password 1",
        password2Label: "Password 2",
        difference: "Difference:",
        recommendation: "Recommendation:",
        refresh: "Refresh",
        example: "For example:",
        batch: "Batch Generation",
        batchCount: "Number of passwords:",
        generateBatch: "Generate Batch",
        downloadCsv: "Download CSV",
        copyAll: "Copy All",
        breachCheck: "Breach Check:",
        breachFound: "⚠️ Password found in data breaches!",
        breachNotFound: "✅ Password not found in breaches",
        breachNote: "Check performed using Have I Been Pwned API"
    }
};

let currentLang = localStorage.getItem('language') || 'ru';

function t(key) {
    return translations[currentLang][key] || key;
}

function setLanguage(lang) {
    currentLang = lang;
    localStorage.setItem('language', lang);
    updateUI();
}

function updateUI() {
    document.querySelectorAll('[data-i18n]').forEach(el => {
        const key = el.getAttribute('data-i18n');
        if (el.tagName === 'INPUT' && el.type !== 'checkbox') {
            el.placeholder = t(key);
        } else {
            el.textContent = t(key);
        }
    });
    
    // Обработка placeholder с отдельным ключом
    document.querySelectorAll('[data-i18n-placeholder]').forEach(el => {
        const key = el.getAttribute('data-i18n-placeholder');
        el.placeholder = t(key) + ' @#$';
    });
}
