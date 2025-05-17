# MoniFi
# A Financial Transaction Monitoring System

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

A robust, real-time financial transaction monitoring system that automatically detects suspicious activities and triggers immediate notifications through configurable workflows.
# N8N
![Integration-au-workflow].(n8n integration fraud alert.JPG)
## 📋 Table of Contents

- [Overview](#overview)
- [Key Features](#key-features)
- [Technology Stack](#technology-stack)
- [System Architecture](#system-architecture)
- [Getting Started](#getting-started)
- [Configuring Fraud Detection Rules](#configuring-fraud-detection-rules)
- [n8n Workflow Configuration](#n8n-workflow-configuration)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Development Guide](#development-guide)
- [License](#license)

## 🔍 Overview

The Financial Transaction Monitoring System is designed to detect potentially fraudulent financial transactions in real-time. It leverages rule-based monitoring to identify suspicious activities, generates alerts based on predefined criteria, and automatically notifies relevant stakeholders through customizable notification workflows.

This system helps financial institutions:
- Detect fraud early to minimize financial losses
- Comply with regulatory requirements for transaction monitoring
- Reduce manual oversight through automation
- Build customer trust through proactive security measures

## ✨ Key Features

- **Real-time Transaction Monitoring**: Evaluates transactions as they occur
- **Flexible Rule Engine**: Configurable rules based on amount, frequency, location, etc.
- **Multi-level Alert System**: Categorizes alerts by severity (High, Medium, Low)
- **Automated Notifications**: Integrates with n8n for customizable notification workflows
- **Comprehensive Dashboard**: Track and manage alerts from a unified interface
- **Detailed Audit Trail**: Complete history of all transactions and alert resolutions
- **RESTful API**: Well-documented API for easy integration with other systems

## 🛠️ Technology Stack

### Backend Framework
- **Spring Boot 2.7.x**: Provides the foundation for the application with minimal configuration
- **Java 11+**: Modern language features and improved performance
- **Spring Data JPA**: Simplifies database operations with repository abstraction
- **Spring Security**: Handles authentication and authorization

### Database
- **MySQL 8.0**: Relational database for storing transaction data, rules, and alerts
- **Hibernate ORM**: Object-relational mapping for database interactions
- **Connection Pooling**: HikariCP for efficient database connection management

### Workflow Automation
- **n8n**: No-code/low-code workflow automation platform
- **Webhook Integration**: Connects the application with n8n workflows
- **Notification Channels**: Email, SMS, Slack, and other notification methods

### Development Tools
- **Maven/Gradle**: Dependency management and build automation
- **JUnit 5**: Comprehensive testing framework
- **Mockito**: Mocking framework for unit tests
- **Lombok**: Reduces boilerplate code

## 🏗️ System Architecture

The system follows a modular architecture with the following components:
