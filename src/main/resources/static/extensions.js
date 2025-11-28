// Дополнительные функции для batch генерации и проверки утечек

// Batch generation
async function generateBatch() {
    const count = parseInt(document.getElementById('batchCount')?.value) || 10;
    const requestBody = {
        count: count,
        length: parseInt(document.getElementById('batchLength')?.value || 16),
        includeLowercase: document.getElementById('batchLowercase')?.checked ?? true,
        includeUppercase: document.getElementById('batchUppercase')?.checked ?? true,
        includeDigits: document.getElementById('batchDigits')?.checked ?? true,
        includeSpecial: document.getElementById('batchSpecial')?.checked ?? false,
        includeCyrillic: document.getElementById('batchCyrillic')?.checked ?? false,
        excludeSimilar: document.getElementById('batchExcludeSimilar')?.checked ?? false
    };

    try {
        const response = await fetch('/api/password/generate/batch', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        });

        const data = await response.json();
        displayBatchResult(data);
    } catch (error) {
        alert('Ошибка: ' + error.message);
    }
}

function displayBatchResult(data) {
    const resultDiv = document.getElementById('batchResult');
    const passwordsList = document.getElementById('batchPasswordsList');
    
    if (passwordsList) {
        passwordsList.innerHTML = data.passwords
            .map((pwd, idx) => `<div class="feedback-item">${idx + 1}. ${pwd}</div>`)
            .join('');
        
        resultDiv?.classList.remove('hidden');
        window.batchPasswords = data.passwords;
    }
}

function downloadCSV() {
    if (!window.batchPasswords) return;
    
    const csv = 'Password\n' + window.batchPasswords.join('\n');
    const blob = new Blob([csv], { type: 'text/csv' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'passwords.csv';
    a.click();
    window.URL.revokeObjectURL(url);
}

function copyAllPasswords() {
    if (!window.batchPasswords) return;
    
    const text = window.batchPasswords.join('\n');
    navigator.clipboard.writeText(text).then(() => {
        alert('Все пароли скопированы!');
    });
}

// Обновление displayValidationResult для показа проверки утечек
const originalDisplayValidationResult = window.displayValidationResult;
window.displayValidationResult = function(data) {
    // Вызов оригинальной функции
    if (originalDisplayValidationResult) {
        originalDisplayValidationResult(data);
    }
    
    // Добавление информации об утечках
    const validationResult = document.getElementById('validationResult');
    if (validationResult && data.isPwned !== undefined) {
        let breachHtml = '';
        if (data.isPwned) {
            breachHtml = `
                <div class="crack-time-display" style="background: linear-gradient(135deg, #f44336 0%, #e91e63 100%);">
                    <div class="crack-time-icon">⚠️</div>
                    <div class="crack-time-label">${t('breachCheck')}</div>
                    <div class="crack-time-value">${t('breachFound')}</div>
                    <div style="font-size: 12px; opacity: 0.9; margin-top: 10px;">${t('breachNote')}</div>
                </div>
            `;
        } else {
            breachHtml = `
                <div class="crack-time-display" style="background: linear-gradient(135deg, #4caf50 0%, #8bc34a 100%);">
                    <div class="crack-time-icon">✅</div>
                    <div class="crack-time-label">${t('breachCheck')}</div>
                    <div class="crack-time-value">${t('breachNotFound')}</div>
                    <div style="font-size: 12px; opacity: 0.9; margin-top: 10px;">${t('breachNote')}</div>
                </div>
            `;
        }
        
        // Вставка после crack-time-display
        const crackTimeDiv = document.querySelector('.crack-time-display');
        if (crackTimeDiv && !document.getElementById('breachCheckDiv')) {
            const breachDiv = document.createElement('div');
            breachDiv.id = 'breachCheckDiv';
            breachDiv.innerHTML = breachHtml;
            crackTimeDiv.parentNode.insertBefore(breachDiv, crackTimeDiv.nextSibling);
        }
    }
};

console.log('Extensions loaded: batch generation and breach check');
