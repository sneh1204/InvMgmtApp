package com.example.invmgmt.db;

import com.example.invmgmt.exception.BaseException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class Data {
    private static long USER_ID_COUNTER = 1;

    private static HashMap<String, Account> accountsMap = new HashMap<String, Account>() {{
        put("a@a.com", new Account("AA", "a@a.com", "test"));
    }};
    private static HashMap<String, Account> authTokenAccountsMap = new HashMap<String, Account>();

    public static AuthResponse login(String email, String password) throws BaseException {
        if (email == null || email.isEmpty()) {
            throw new BaseException("Email cannot be empty!");
        } else {
            Account account = accountsMap.get(email.toLowerCase());
            if (account == null) {
                throw new BaseException("Email/Account not found!");
            } else {
                if (!account.getPassword().equals(password)) {
                    throw new BaseException("Email or Password do not match!");
                } else {
                    String token = UUID.randomUUID().toString();
                    authTokenAccountsMap.clear();
                    authTokenAccountsMap.put(token, account);
                    return new AuthResponse(account, token);
                }
            }
        }
    }

    public static AuthResponse register(String name, String email, String password) throws BaseException {
        if (name == null || name.isEmpty()) {
            throw new BaseException("Name cannot be empty!");
        } else if (email == null || email.isEmpty()) {
            throw new BaseException("Email cannot be empty!");
        } else if (password == null || password.isEmpty()) {
            throw new BaseException("Password cannot be empty!");
        } else {
            if (accountsMap.containsKey(email.toLowerCase())) {
                throw new BaseException("Email already used please use another email!");
            } else {
                Account account = new Account(name, email.trim().toLowerCase(), password);
                accountsMap.put(email.trim().toLowerCase(), account);
                String token = UUID.randomUUID().toString();
                authTokenAccountsMap.clear();
                authTokenAccountsMap.put(token, account);
                return new AuthResponse(account, token);
            }

        }
    }

    public static Account getAccount(String token) throws BaseException {
        if (token == null) {
            throw new BaseException("Token is required!");
        } else {
            Account account = authTokenAccountsMap.get(token);
            if (account == null) {
                throw new BaseException("Invalid token account not found!");
            } else {
                return account;
            }
        }
    }

    public static class Account implements Serializable {
        long uid;
        private String name, email, password;

        public Account(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.uid = USER_ID_COUNTER++;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Account account = (Account) o;
            return uid == account.uid;
        }

        @Override
        public int hashCode() {
            return (int) (uid % (1024 * 1024));
        }

        @Override
        public String toString() {
            return "Account{" +
                    "name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", uid=" + uid +
                    '}';
        }
    }

    public static class AuthResponse implements Serializable {
        Account account;
        String token;

        public AuthResponse(Account account, String token) {
            this.account = account;
            this.token = token;
        }

        public Account getAccount() {
            return account;
        }

        public void setAccount(Account account) {
            this.account = account;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

}
