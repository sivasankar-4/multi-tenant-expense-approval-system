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


# ✨ Key Features

## 🔐 Authentication & Authorization

- JWT-based authentication
- Access token + refresh token flow
- BCrypt password hashing
- Role-based access control
- Method-level authorization with Spring Security
- Role-aware approval actions
- `401 Unauthorized` for unauthenticated access
- `403 Forbidden` for authenticated users without sufficient permissions

Supported roles:

```text
EMPLOYEE
MANAGER
FINANCE_ADMIN

🏢 Multi-Tenancy

The application uses a shared-database multi-tenant model.

Each user belongs to exactly one tenant, and tenant context is derived from the authenticated user rather than being blindly trusted from the request payload.

Conceptually:

                         Application
                              │
              ┌───────────────┴───────────────┐
              │                               │
           Tenant A                        Tenant B
              │                               │
       ┌──────┼──────┐                 ┌──────┼──────┐
       │      │      │                 │      │      │
      Users Expenses Approvals        Users Expenses Approvals

Tenant isolation is enforced through application-level checks and database-level integrity constraints.

Production tenant-isolation verification

A user belonging to one tenant was deliberately used to access an expense belonging to another tenant.

Result:

Tenant B user
     ↓
Tenant A expense
     ↓
403 Forbidden

This verifies that cross-tenant access is rejected in the deployed application.

---
💰 Expense Management

Employees can submit expenses containing information such as:

Amount
Currency
Category
Description

The expense belongs to:

Tenant
   ↓
Submitting User
   ↓
Expense

Expense lifecycle:

PENDING
   ↓
IN_REVIEW
   ↓
APPROVED

or:

PENDING / IN_REVIEW
   ↓
REJECTED
