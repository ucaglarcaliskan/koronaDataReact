import React, { useEffect } from 'react';

export type ToastType = 'success' | 'error' | 'warning' | 'info';

interface ToastProps {
    message: string;
    type: ToastType;
    onClose: () => void;
    duration?: number;
}

const Toast: React.FC<ToastProps> = ({ message, type, onClose, duration = 3000 }) => {
    useEffect(() => {
        const timer = setTimeout(() => {
            onClose();
        }, duration);

        return () => clearTimeout(timer);
    }, [onClose, duration]);

    const getToastStyles = () => {
        const baseStyles = {
            position: 'fixed' as const,
            top: '20px',
            right: '20px',
            padding: '16px 20px',
            borderRadius: '8px',
            color: 'white',
            fontWeight: 'bold',
            fontSize: '14px',
            zIndex: 1000,
            minWidth: '300px',
            maxWidth: '500px',
            boxShadow: '0 4px 12px rgba(0, 0, 0, 0.15)',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'space-between',
            animation: 'slideInRight 0.3s ease-out',
            cursor: 'pointer'
        };

        const typeStyles = {
            success: { backgroundColor: '#4caf50' },
            error: { backgroundColor: '#f44336' },
            warning: { backgroundColor: '#ff9800' },
            info: { backgroundColor: '#2196f3' }
        };

        return { ...baseStyles, ...typeStyles[type] };
    };

    const getIcon = () => {
        const icons = {
            success: '✅',
            error: '❌',
            warning: '⚠️',
            info: 'ℹ️'
        };
        return icons[type];
    };

    return (
        <>
            <style>
                {`
          @keyframes slideInRight {
            from {
              transform: translateX(100%);
              opacity: 0;
            }
            to {
              transform: translateX(0);
              opacity: 1;
            }
          }
          
          @keyframes slideOutRight {
            from {
              transform: translateX(0);
              opacity: 1;
            }
            to {
              transform: translateX(100%);
              opacity: 0;
            }
          }
        `}
            </style>
            <div style={getToastStyles()} onClick={onClose}>
                <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
                    <span style={{ fontSize: '16px' }}>{getIcon()}</span>
                    <span>{message}</span>
                </div>
                <button
                    onClick={(e) => {
                        e.stopPropagation();
                        onClose();
                    }}
                    style={{
                        background: 'none',
                        border: 'none',
                        color: 'white',
                        fontSize: '18px',
                        cursor: 'pointer',
                        padding: '0 5px',
                        opacity: 0.8
                    }}
                    onMouseEnter={(e) => {
                        e.currentTarget.style.opacity = '1';
                    }}
                    onMouseLeave={(e) => {
                        e.currentTarget.style.opacity = '0.8';
                    }}
                >
                    ×
                </button>
            </div>
        </>
    );
};

export default Toast; 