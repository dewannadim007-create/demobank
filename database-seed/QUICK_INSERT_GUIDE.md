# 🚀 Quick Insert Guide - MongoDB Demo Data

## Method 1: Using MongoDB Shell (mongosh) - RECOMMENDED

### Step 1: Connect to MongoDB
```bash
mongosh "mongodb+srv://nadim:southeast@class.xtowejo.mongodb.net/mobile-banking"
```

### Step 2: Run the Insert Script
```bash
# Load and execute the script
load("D:/SPRINGBOOT_TRY/database-seed/insert-demo-data.js")
```

**OR** Copy-paste commands directly from `insert-demo-data.js`

---

## Method 2: Individual Insert Commands

### INSERT USERS (Copy all at once)
```javascript
db.users.insertMany([
  {
    "name": "Admin User",
    "mobile": "01700000001",
    "email": "admin@bank.com",
    "password": "admin123",
    "DOB": "1990-01-01",
    "account": "ADMIN001",
    "nid": "9999999999999",
    "balance": 0,
    "userRole": "ADMIN",
    "address": "Head Office, Dhaka",
    "image": null
  },
  {
    "name": "John Doe",
    "mobile": "01700000002",
    "email": "john@gmail.com",
    "password": "john1234",
    "DOB": "1995-05-15",
    "account": "ACC001",
    "nid": "1234567890123",
    "balance": 50000,
    "userRole": "USER",
    "address": "Dhanmondi, Dhaka",
    "image": null
  },
  {
    "name": "Sarah Khan",
    "mobile": "01800000003",
    "email": "sarah@yahoo.com",
    "password": "sarah123",
    "DOB": "1998-08-22",
    "account": "ACC002",
    "nid": "1234567890124",
    "balance": 75000,
    "userRole": "USER",
    "address": "Gulshan, Dhaka",
    "image": null
  },
  {
    "name": "Rahim Ahmed",
    "mobile": "01900000004",
    "email": "rahim@gmail.com",
    "password": "rahim123",
    "DOB": "1992-03-10",
    "account": "ACC003",
    "nid": "1234567890125",
    "balance": 100000,
    "userRole": "USER",
    "address": "Mirpur, Dhaka",
    "image": null
  },
  {
    "name": "Ayesha Rahman",
    "mobile": "01600000005",
    "email": "ayesha@outlook.com",
    "password": "ayesha12",
    "DOB": "1996-11-30",
    "account": "ACC004",
    "nid": "1234567890126",
    "balance": 60000,
    "userRole": "USER",
    "address": "Uttara, Dhaka",
    "image": null
  }
]);
```

**Expected:** `{ acknowledged: true, insertedIds: {...} }` with 5 IDs

---

### INSERT BRANCHES (Copy all at once)
```javascript
db.branches.insertMany([
  {
    "name": "Motijheel Main Branch",
    "type": "Main",
    "location": "23/A Motijheel C/A, Dhaka-1000",
    "branchCode": "BR001",
    "city": "Dhaka",
    "phoneNumber": "+880-2-9555444",
    "email": "motijheel@bank.com",
    "managerName": "Mr. Kamal Hossain",
    "openingHours": "9:00 AM - 5:00 PM",
    "isActive": true
  },
  {
    "name": "Gulshan Branch",
    "type": "Main",
    "location": "House 45, Road 11, Gulshan-1, Dhaka-1212",
    "branchCode": "BR002",
    "city": "Dhaka",
    "phoneNumber": "+880-2-8822334",
    "email": "gulshan@bank.com",
    "managerName": "Ms. Fatima Begum",
    "openingHours": "9:00 AM - 5:00 PM",
    "isActive": true
  },
  {
    "name": "Dhanmondi Branch",
    "type": "Sub-branch",
    "location": "Plot 15, Road 27, Dhanmondi, Dhaka-1209",
    "branchCode": "BR003",
    "city": "Dhaka",
    "phoneNumber": "+880-2-9661234",
    "email": "dhanmondi@bank.com",
    "managerName": "Mr. Rahman Ali",
    "openingHours": "9:30 AM - 4:30 PM",
    "isActive": true
  },
  {
    "name": "Mirpur ATM",
    "type": "ATM",
    "location": "Section 2, Mirpur, Dhaka-1216",
    "branchCode": "ATM001",
    "city": "Dhaka",
    "phoneNumber": "+880-2-9001122",
    "openingHours": "24/7",
    "isActive": true
  },
  {
    "name": "Uttara Branch",
    "type": "Main",
    "location": "Sector 7, Uttara, Dhaka-1230",
    "branchCode": "BR004",
    "city": "Dhaka",
    "phoneNumber": "+880-2-8951234",
    "email": "uttara@bank.com",
    "managerName": "Ms. Nadia Islam",
    "openingHours": "9:00 AM - 5:00 PM",
    "isActive": true
  },
  {
    "name": "Banani ATM",
    "type": "ATM",
    "location": "Road 11, Banani, Dhaka-1213",
    "branchCode": "ATM002",
    "city": "Dhaka",
    "phoneNumber": "+880-2-9882200",
    "openingHours": "24/7",
    "isActive": true
  },
  {
    "name": "Chattogram Main Branch",
    "type": "Main",
    "location": "Agrabad C/A, Chattogram-4100",
    "branchCode": "BR005",
    "city": "Chattogram",
    "phoneNumber": "+880-31-2520000",
    "email": "chattogram@bank.com",
    "managerName": "Mr. Shafiq Ahmed",
    "openingHours": "9:00 AM - 5:00 PM",
    "isActive": true
  },
  {
    "name": "Sylhet Branch",
    "type": "Main",
    "location": "Zindabazar, Sylhet-3100",
    "branchCode": "BR006",
    "city": "Sylhet",
    "phoneNumber": "+880-821-720000",
    "email": "sylhet@bank.com",
    "managerName": "Mr. Habib Rahman",
    "openingHours": "9:00 AM - 4:30 PM",
    "isActive": true
  }
]);
```

**Expected:** `{ acknowledged: true, insertedIds: {...} }` with 8 IDs

---

### INSERT UTILITY ACCOUNTS (Optional - Copy all at once)
```javascript
db.utility.insertMany([
  {
    "account": "TITAS001",
    "provider": "titas",
    "type": "prepaid",
    "balance": 0,
    "customerName": "John Doe",
    "address": "Dhanmondi, Dhaka"
  },
  {
    "account": "TITAS002",
    "provider": "titas",
    "type": "prepaid",
    "balance": 500,
    "customerName": "Sarah Khan",
    "address": "Gulshan, Dhaka"
  },
  {
    "account": "DESCO001",
    "provider": "DESCO",
    "type": "prepaid",
    "balance": 0,
    "customerName": "Rahim Ahmed",
    "address": "Mirpur, Dhaka"
  },
  {
    "account": "DPDC001",
    "provider": "DPDC",
    "type": "postpaid",
    "balance": 1200,
    "customerName": "Ayesha Rahman",
    "address": "Uttara, Dhaka"
  },
  {
    "account": "01700000002",
    "provider": "Grameenphone",
    "type": "prepaid",
    "balance": 50,
    "customerName": "John Doe"
  },
  {
    "account": "01800000003",
    "provider": "Robi",
    "type": "prepaid",
    "balance": 20,
    "customerName": "Sarah Khan"
  }
]);
```

**Expected:** `{ acknowledged: true, insertedIds: {...} }` with 6 IDs

---

## ✅ Verification Commands

After inserting, verify with:

```javascript
// Count documents
db.users.countDocuments();      // Should return: 5
db.branches.countDocuments();   // Should return: 8
db.utility.countDocuments();    // Should return: 6

// View all data
db.users.find().pretty();
db.branches.find().pretty();
db.utility.find().pretty();

// Check specific user
db.users.findOne({ mobile: "01700000002" });

// Check admin
db.users.findOne({ userRole: "ADMIN" });
```

---

## 🎯 Quick Test After Insert

### Test Admin Login:
- URL: http://localhost:5896/admin/login
- ID: `ADMIN001`
- Password: `admin123`

### Test User Login:
- URL: http://localhost:5896/login
- Mobile: `01700000002`
- Password: `john1234`

---

## 🚨 Troubleshooting

### "Duplicate key error"
**Cause:** Data already exists  
**Fix:** Drop collections first:
```javascript
db.users.drop();
db.branches.drop();
db.utility.drop();
// Then run inserts again
```

### "Connection refused"
**Cause:** Wrong connection string  
**Fix:** Check your MongoDB Atlas connection string

---

## 📦 Files in This Directory

- `insert-demo-data.js` - Complete MongoDB insert script
- `users.json` - Users data (for MongoDB Compass)
- `branches.json` - Branches data (for MongoDB Compass)
- `utility.json` - Utility data (for MongoDB Compass)

---

**Choose the method that's easiest for you!** 🎉
