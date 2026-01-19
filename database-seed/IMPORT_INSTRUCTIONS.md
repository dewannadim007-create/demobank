# 📊 MongoDB Database Import Instructions

## Quick Import to MongoDB Atlas

### Method 1: MongoDB Compass (EASIEST)

1. **Open MongoDB Compass**
2. **Connect** to your MongoDB Atlas:
   ```
   mongodb+srv://nadim:southeast@class.xtowejo.mongodb.net/mobile-banking
   ```

3. **Select Database**: `mobile-banking`

4. **Import Each Collection:**

   **For `users` collection:**
   - Click on `users` collection (or create it)
   - Click **"ADD DATA"** → **"Import JSON or CSV file"**
   - Select `users.json`
   - Click **Import**

   **For `branches` collection:**
   - Click on `branches` collection (or create it)
   - Click **"ADD DATA"** → **"Import JSON or CSV file"**
   - Select `branches.json`
   - Click **Import**

   **For `utility` collection (Optional):**
   - Click on `utility` collection (or create it)
   - Click **"ADD DATA"** → **"Import JSON or CSV file"**
   - Select `utility.json`
   - Click **Import**

---

### Method 2: MongoDB Atlas Web UI

1. **Login to MongoDB Atlas**: https://cloud.mongodb.com
2. **Navigate to**: Clusters → Browse Collections
3. **Select Database**: `mobile-banking`
4. **For each collection:**
   - Click **"INSERT DOCUMENT"**
   - Switch to **"{ } JSON View"**
   - **Copy entire content** from JSON file
   - Paste and click **Insert**
   - Repeat for all documents in the file

---

### Method 3: mongosh (Command Line)

If you have `mongosh` installed:

```bash
# Connect to your database
mongosh "mongodb+srv://nadim:southeast@class.xtowejo.mongodb.net/mobile-banking"

# Import users
mongoimport --uri "mongodb+srv://nadim:southeast@class.xtowejo.mongodb.net/mobile-banking" --collection users --file users.json --jsonArray

# Import branches
mongoimport --uri "mongodb+srv://nadim:southeast@class.xtowejo.mongodb.net/mobile-banking" --collection branches --file branches.json --jsonArray

# Import utility (optional)
mongoimport --uri "mongodb+srv://nadim:southeast@class.xtowejo.mongodb.net/mobile-banking" --collection utility --file utility.json --jsonArray
```

---

## ✅ Test Login Credentials

After importing, you can test with:

### Admin Login:
- **Username/ID**: `ADMIN001`
- **Password**: `admin123`

### User Logins:
1. **John Doe**
   - Mobile: `01700000002`
   - Password: `john1234`
   - Balance: ৳50,000

2. **Sarah Khan**
   - Mobile: `01800000003`
   - Password: `sarah123`
   - Balance: ৳75,000

3. **Rahim Ahmed**
   - Mobile: `01900000004`
   - Password: `rahim123`
   - Balance: ৳100,000

4. **Ayesha Rahman**
   - Mobile: `01600000005`
   - Password: `ayesha12`
   - Balance: ৳60,000

---

## 🎯 Verification Steps

After import, verify in MongoDB Compass:

1. ✅ **users** collection has **5 documents** (1 admin + 4 users)
2. ✅ **branches** collection has **8 documents**
3. ✅ **utility** collection has **6 documents** (optional)
4. ✅ **transactions** collection is **empty** (will populate as you use the app)

---

## 🚀 Start Your Application

After importing:

```powershell
cd D:\SPRINGBOOT_TRY
.\apache-maven-3.9.12\bin\mvn.cmd spring-boot:run
```

**Access your app at**: http://localhost:5896/first

🎉 **Ready to test all features!**
