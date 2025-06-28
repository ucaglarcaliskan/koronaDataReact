import React, { useState } from 'react';

interface AccordionProps {
    title: string;
    children: React.ReactNode;
    defaultOpen?: boolean;
}

const Accordion: React.FC<AccordionProps> = ({ title, children, defaultOpen = false }) => {
    const [isOpen, setIsOpen] = useState(defaultOpen);

    const toggleAccordion = () => {
        setIsOpen(!isOpen);
    };

    return (
        <div style={{
            border: '1px solid #ddd',
            borderRadius: '8px',
            margin: '10px 0',
            boxShadow: '0 2px 4px rgba(0,0,0,0.1)'
        }}>
            <div
                onClick={toggleAccordion}
                style={{
                    padding: '15px 20px',
                    backgroundColor: '#f8f9fa',
                    cursor: 'pointer',
                    borderRadius: isOpen ? '8px 8px 0 0' : '8px',
                    display: 'flex',
                    justifyContent: 'space-between',
                    alignItems: 'center',
                    fontWeight: 'bold',
                    fontSize: '16px',
                    transition: 'background-color 0.2s ease'
                }}
                onMouseEnter={(e) => {
                    e.currentTarget.style.backgroundColor = '#e9ecef';
                }}
                onMouseLeave={(e) => {
                    e.currentTarget.style.backgroundColor = '#f8f9fa';
                }}
            >
                <span>{title}</span>
                <span style={{
                    fontSize: '18px',
                    transform: isOpen ? 'rotate(180deg)' : 'rotate(0deg)',
                    transition: 'transform 0.2s ease'
                }}>
                    ▼
                </span>
            </div>
            {isOpen && (
                <div style={{
                    padding: '20px',
                    borderTop: '1px solid #ddd',
                    backgroundColor: '#fff'
                }}>
                    {children}
                </div>
            )}
        </div>
    );
};

export default Accordion; 