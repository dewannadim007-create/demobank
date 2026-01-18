# HTML Templates Guide

## ✅ Created Templates (7/30)

| Template | Purpose | Status |
|----------|---------|--------|
| `first.html` | Landing page | ✅ Created |
| `login.html` | User login | ✅ Created |
| `registration.html` | User registration | ✅ Created |
| `home.html` | User dashboard | ✅ Created |
| `addMoney.html` | Add money/deposit | ✅ Created |
| `sendMoney.html` | Transfer money | ✅ Created |
| `menu.html` | Main menu | ✅ Created |

## 📝 Remaining Templates (23/30)

### Admin & User Management
- `adminLogin.html` - Admin authentication page
- `adminHome.html` - Admin dashboard
- `adminMenu.html` - Admin menu/navigation
- `addUser.html` - Add new user (admin)
- `userList.html` - List all users (admin)
- `users.html` - User management
- `userProfile.html` - User profile details
- `changePassword.html` - Password change form

### Transactions
- `transactionList.html` - All transactions list
- `transactionProfile.html` - Transaction details
- `statement.html` - Account statement
- `payment.html` - Payment page

### Banking Services
- `checkBook.html` - Checkbook request
- `chequeBookStatus.html` - Check status
- `branch.html` - Branch finder
- `eBanking.html` - E-banking services

### Utility & Recharge
- `recharge.html` - Mobile recharge
- `utility.html` - Utility bill payments
- `gas.html` - Gas bill payment

### Other Pages
- `faq.html` - Frequently asked questions
- `bb.html`, `bW.html`, `wB.html`, `wW.html` - Utility pages

## 🎨 Template Structure

All templates follow this structure:

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page Title - Mobile Banking</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
    <div class="page-wrapper">
        <header class="header">
            <div class="container">
                <h1>Page Title</h1>
                <p>Description</p>
            </div>
        </header>

        <nav class="navbar">
            <!-- Navigation links -->
        </nav>

        <main class="container fade-in">
            <!-- Content -->
        </main>

        <footer class="footer">
            <p>&copy; 2026 Mobile Banking System. All rights reserved.</p>
        </footer>
    </div>
</body>
</html>
```

## 🔗 Next Steps

To complete all 30 pages:

1. Use the created templates as reference
2. Each page should use Thymeleaf syntax (`th:*` attributes)
3. All forms should submit to appropriate controller endpoints
4. Use the shared `style.css` for consistent styling
5. Include navigation between pages

## 📍 File Location

All templates stored in:
```
d:\SPRINGBOOT_TRY\src\main\resources\templates\
```

Shared CSS in:
```
d:\SPRINGBOOT_TRY\src\main\resources\static\css\style.css
```
