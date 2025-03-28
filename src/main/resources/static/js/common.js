/**
 * Общие скрипты для приложения
 * @version 1.1
 */

document.addEventListener('DOMContentLoaded', function() {
    // Инициализация анимаций
    initAnimations();
    
    // Инициализация форм
    initForms();
    
    // Инициализация интерактивных элементов
    initInteractiveElements();
});

/**
 * Настройка анимаций
 */
function initAnimations() {
    // Анимация появления форм
    const animateElements = document.querySelectorAll('.auth-form, .hero');
    animateElements.forEach(el => {
        el.style.opacity = '0';
        el.style.transform = 'translateY(20px)';
        el.style.transition = 'all 0.6s ease-out';
        
        setTimeout(() => {
            el.style.opacity = '1';
            el.style.transform = 'translateY(0)';
        }, 100);
    });

    // Плавное появление элементов при скролле
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('visible');
            }
        });
    }, { threshold: 0.1 });

    document.querySelectorAll('.animate-on-scroll').forEach(el => {
        observer.observe(el);
    });
}

/**
 * Настройка форм
 */
function initForms() {
    // Валидация паролей
    const passwordFields = document.querySelectorAll('input[type="password"]');
    passwordFields.forEach(field => {
        field.addEventListener('input', function() {
            if (this.value.length > 0) {
                const isValid = this.checkValidity();
                this.style.borderColor = isValid ? '#28a745' : '#dc3545';
                
                if (this.id === 'confirm-password') {
                    const password = document.getElementById('password');
                    if (password && this.value !== password.value) {
                        this.style.borderColor = '#dc3545';
                    }
                }
            }
        });
    });

    // Анимация кнопок формы
    const formButtons = document.querySelectorAll('form button[type="submit"]');
    formButtons.forEach(btn => {
        btn.addEventListener('mouseenter', () => {
            btn.style.transform = 'translateY(-3px)';
            btn.style.boxShadow = '0 6px 12px rgba(0,0,0,0.15)';
        });
        
        btn.addEventListener('mouseleave', () => {
            btn.style.transform = '';
            btn.style.boxShadow = '';
        });
        
        btn.addEventListener('mousedown', () => {
            btn.style.transform = 'scale(0.98)';
        });
        
        btn.addEventListener('mouseup', () => {
            btn.style.transform = 'translateY(-3px)';
        });
    });
}

/**
 * Настройка интерактивных элементов
 */
function initInteractiveElements() {
    // Анимация кнопок в хедере
    const headerButtons = document.querySelectorAll('.header-nav .btn');
    headerButtons.forEach(btn => {
        btn.addEventListener('mouseenter', () => {
            btn.style.transform = 'scale(1.05)';
        });
        
        btn.addEventListener('mouseleave', () => {
            btn.style.transform = '';
        });
    });

    // Показать год в футере (альтернатива Thymeleaf)
    const yearElement = document.querySelector('.current-year');
    if (yearElement) {
        yearElement.textContent = new Date().getFullYear();
    }

    // Плавный скролл для якорей
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
            document.querySelector(this.getAttribute('href')).scrollIntoView({
                behavior: 'smooth'
            });
        });
    });
}

/**
 * Вспомогательные функции
 */
function showToast(message, type = 'success') {
    // Реализация toast-уведомлений
    const toast = document.createElement('div');
    toast.className = `toast toast-${type}`;
    toast.textContent = message;
    document.body.appendChild(toast);
    
    setTimeout(() => {
        toast.classList.add('show');
    }, 100);
    
    setTimeout(() => {
        toast.remove();
    }, 3000);
}