// Batch generation functions
async function generateBatch() {
    const count = parseInt(document.getElementById('batchCount').value) || 10;
    const requestBody = {
        count: count,
        length: parseInt(document.getElementById('batchLength').value),
        includeLowercase: document.getElementById('batchLowercase').checked,
        includeUppercase: document.getElementById('batchUppercase').checked,
        includeDigits: document.getElementById('batchDigits').checked,
        includeSpecial: document.getElementById('batchSpecial').checked,
        includeCyrillic: document.getElementById('batchCyrillic').checked,
        excludeSimilar: document.getElementById('batchExcludeSimilar').checked
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
    
    passwordsList.innerHTML = data.passwords
        .map((pwd, idx) => `<div class="feedback-item">${idx + 1}. ${pwd}</div>`)
        .join('');
    
    resultDiv.classList.remove('hidden');
    window.batchPasswords = data.passwords;
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
}

function copyAllPasswords() {
    if (!window.batchPasswords) return;
    
    const text = window.batchPasswords.join('\n');
    navigator.clipboard.writeText(text).then(() => {
        alert('Все пароли скопированы!');
    });
}
