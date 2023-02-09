import SimpleCalculation from "./SimpleCalculation";
import ComplexCalculation from "./ComplexCalculation";
import { ToastContainer } from "react-toastify";
import "./App.css";

function App() {
  return (
    <div className="App">
      <SimpleCalculation />
      <div className="mt-5">
        <ComplexCalculation />
      </div>

      <ToastContainer />
    </div>
  );
}

export default App;
