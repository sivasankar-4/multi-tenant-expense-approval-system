# Multi-Tenant Expense Approval System

A multi-tenant SaaS backend where companies can manage expenses through
configurable, role-based approval workflows.

Built with Java, Spring Boot, Spring Security, MySQL, Flyway and JWT.

### Highlights

- 🔐 JWT authentication + RBAC
- 🏢 Tenant-isolated data access
- 🔄 Multi-step, amount-based approval workflows
- 📝 Approval and audit history
- 🗄️ Versioned database migrations with Flyway
- ☁️ Deployed on Railway

**Live API:** https://multi-tenant-expense-approval-system-production.up.railway.app/

## Problem

In many organizations, expense approvals are handled through email,
spreadsheets or informal processes. This makes approval routing,
accountability and auditability difficult.

This project models a SaaS-style expense approval system where each
company operates as an isolated tenant with configurable approval rules.

Each company can have:

- Its own users
- Its own expenses
- Its own approval chains
- Its own approval history
- Its own audit records

---

