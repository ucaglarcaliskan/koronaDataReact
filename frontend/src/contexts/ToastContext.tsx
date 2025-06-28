import React, { createContext, useContext } from 'react';
import type { ReactNode } from 'react';
import { useToast } from '../hooks/useToast';
import { Toast } from '../components/ui';
import type { ToastType } from '../components/ui';

interface ToastContextType {
    showSuccess: (message: string) => void;
    showError: (message: string) => void;
    showWarning: (message: string) => void;
    showInfo: (message: string) => void;
    showToast: (message: string, type: ToastType) => void;
}

const ToastContext = createContext<ToastContextType | undefined>(undefined);

interface ToastProviderProps {
    children: ReactNode;
}

export const ToastProvider: React.FC<ToastProviderProps> = ({ children }) => {
    const { toasts, removeToast, showSuccess, showError, showWarning, showInfo, showToast } = useToast();

    return (
        <ToastContext.Provider value={{ showSuccess, showError, showWarning, showInfo, showToast }}>
            {children}

            {/* Toast Container */}
            <div style={{
                position: 'fixed',
                top: '20px',
                right: '20px',
                zIndex: 1000,
                display: 'flex',
                flexDirection: 'column',
                gap: '10px'
            }}>
                {toasts.map((toast, index) => (
                    <div
                        key={toast.id}
                        style={{
                            transform: `translateY(${index * 10}px)`,
                            transition: 'transform 0.3s ease'
                        }}
                    >
                        <Toast
                            message={toast.message}
                            type={toast.type}
                            onClose={() => removeToast(toast.id)}
                        />
                    </div>
                ))}
            </div>
        </ToastContext.Provider>
    );
};

export const useToastContext = (): ToastContextType => {
    const context = useContext(ToastContext);
    if (!context) {
        throw new Error('useToastContext must be used within a ToastProvider');
    }
    return context;
}; 