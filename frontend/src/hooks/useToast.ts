import { useState, useCallback } from 'react';
import type { ToastType } from '../components/ui/Toast';

interface ToastMessage {
    id: string;
    message: string;
    type: ToastType;
}

export const useToast = () => {
    const [toasts, setToasts] = useState<ToastMessage[]>([]);

    const showToast = useCallback((message: string, type: ToastType) => {
        const id = Date.now().toString();
        const newToast: ToastMessage = { id, message, type };

        setToasts(prevToasts => [...prevToasts, newToast]);
    }, []);

    const removeToast = useCallback((id: string) => {
        setToasts(prevToasts => prevToasts.filter(toast => toast.id !== id));
    }, []);

    // Convenience methods
    const showSuccess = useCallback((message: string) => {
        showToast(message, 'success');
    }, [showToast]);

    const showError = useCallback((message: string) => {
        showToast(message, 'error');
    }, [showToast]);

    const showWarning = useCallback((message: string) => {
        showToast(message, 'warning');
    }, [showToast]);

    const showInfo = useCallback((message: string) => {
        showToast(message, 'info');
    }, [showToast]);

    return {
        toasts,
        showToast,
        removeToast,
        showSuccess,
        showError,
        showWarning,
        showInfo
    };
}; 