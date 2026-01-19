
// Admin Dashboard Scripts

// Cheque Book Status Page
function initChequeStatus() {
    const table = document.querySelector('.cheque-table');
    if (!table) return;

    window.selectRow = function (row) {
        document.querySelectorAll('.cheque-table tbody tr').forEach(r => r.classList.remove('selected-row'));
        row.classList.add('selected-row');

        const account = row.cells[0].textContent.trim();
        const selectedAccountInput = document.getElementById('selectedAccount');
        const displayAccountSpan = document.getElementById('displayAccount');
        const actionPanel = document.getElementById('actionPanel');
        const instructionText = document.getElementById('instructionText');

        if (selectedAccountInput) selectedAccountInput.value = account;
        if (displayAccountSpan) displayAccountSpan.textContent = account;

        if (actionPanel) actionPanel.style.display = 'flex';
        if (instructionText) instructionText.style.display = 'none';
    };
}

// User List Page
function initUserList() {
    const table = document.querySelector('.user-table');
    if (!table) return;

    window.selectUser = function (row) {
        document.querySelectorAll('.user-table tbody tr').forEach(r => r.classList.remove('selected-row'));
        row.classList.add('selected-row');
        const mobileCell = row.querySelector('.mobile-cell');
        if (mobileCell) {
            const mobile = mobileCell.textContent.trim();
            window.location.href = '/admin/users/balance?mobile=' + mobile;
        }
    };
}

document.addEventListener('DOMContentLoaded', function () {
    initChequeStatus();
    initUserList();
});
