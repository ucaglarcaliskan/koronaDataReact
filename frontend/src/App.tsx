import './App.css'
import { DataScreen } from './components/screens'
import { ToastProvider } from './contexts/ToastContext'

function App() {
  return (
    <ToastProvider>
      <div className="App">
        <DataScreen />
      </div>
    </ToastProvider>
  )
}

export default App
