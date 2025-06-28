import React, { useState } from 'react';
import axios from 'axios';
import { useToastContext } from '../../contexts/ToastContext';

interface NewsInputProps {
    onNewsAdded: () => void;
}

const NewsInput: React.FC<NewsInputProps> = ({ onNewsAdded }) => {
    const [inputText, setInputText] = useState<string>('');
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const { showSuccess, showError, showWarning } = useToastContext();

    const handleSendText = async () => {
        if (!inputText.trim()) {
            showWarning('Lütfen bir metin girin.');
            return;
        }

        setIsLoading(true);
        try {
            await axios.post('http://localhost:8080/api/news', { text: inputText });
            setInputText('');
            showSuccess('Haber başarıyla eklendi!');
            onNewsAdded();
        } catch (error: any) {
            console.error('Metin gönderilirken hata oluştu:', error);

            if (error.response?.status === 400) {
                const errorMessage = error.response.data || 'Haber eklenirken bir hata oluştu';
                showError(errorMessage);
            } else if (error.response?.status === 500) {
                showError('Sunucu hatası oluştu. Lütfen tekrar deneyin.');
            } else {
                showError('Bağlantı hatası. Lütfen internet bağlantınızı kontrol edin.');
            }
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div style={{ display: 'flex', alignItems: 'flex-start', gap: '10px', justifyContent: 'center' }}>
            <textarea
                value={inputText}
                onChange={(e) => setInputText(e.target.value)}
                placeholder="Yeni haber girin..."
                disabled={isLoading}
                style={{
                    width: '500px',
                    height: '100px',
                    padding: '10px',
                    fontSize: '16px',
                    resize: 'vertical',
                    fontFamily: 'inherit',
                    border: '2px solid #ddd',
                    borderRadius: '8px',
                    outline: 'none',
                    transition: 'border-color 0.2s ease',
                    opacity: isLoading ? 0.6 : 1
                }}
                onFocus={(e) => {
                    if (!isLoading) e.target.style.borderColor = '#007bff';
                }}
                onBlur={(e) => {
                    e.target.style.borderColor = '#ddd';
                }}
            />
            <button
                onClick={handleSendText}
                disabled={isLoading}
                style={{
                    padding: '10px 20px',
                    fontSize: '16px',
                    cursor: isLoading ? 'not-allowed' : 'pointer',
                    height: 'fit-content',
                    backgroundColor: isLoading ? '#6c757d' : '#007bff',
                    color: 'white',
                    border: 'none',
                    borderRadius: '8px',
                    transition: 'background-color 0.2s ease',
                    opacity: isLoading ? 0.6 : 1
                }}
                onMouseEnter={(e) => {
                    if (!isLoading) e.currentTarget.style.backgroundColor = '#0056b3';
                }}
                onMouseLeave={(e) => {
                    if (!isLoading) e.currentTarget.style.backgroundColor = '#007bff';
                }}
            >
                {isLoading ? 'Ekleniyor...' : 'Ekle'}
            </button>
        </div>
    );
};

export default NewsInput; 