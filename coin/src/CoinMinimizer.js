import React, { useState } from "react";
import axios from "axios";

const CoinMinimizer = () => {
  const [targetAmount, setTargetAmount] = useState("");
  const [selectedDenominations, setSelectedDenominations] = useState([]);
  const [result, setResult] = useState("");
  const [error, setError] = useState("");

  const validDenominations = [
    0.01, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 50.0, 100.0, 1000.0,
  ];

  const handleDenominationChange = (event) => {
    const value = parseFloat(event.target.value);
    setSelectedDenominations((prevSelected) =>
      event.target.checked
        ? [...prevSelected, value]
        : prevSelected.filter((denom) => denom !== value)
    );
  };

  const handleSubmit = async () => {
    try {
      setError("");
      const response = await axios.post(
        "http://18.136.103.66:8080/api/coins/minimize",
        {
          targetAmount: parseFloat(targetAmount),
          coinDenominations: selectedDenominations,
        }
      );
      setResult(response.data);
    } catch (err) {
      setError("An error occurred while processing the request.");
    }
  };

  return (
    <div>
      <h1>Coin Minimizer</h1>
      <div>
        <label>
          Target Amount:
          <input
            type="number"
            value={targetAmount}
            onChange={(e) => setTargetAmount(e.target.value)}
            step="0.01"
            min="0"
            max="10000"
          />
        </label>
      </div>

      <div>
        <h3>Select Denominations</h3>
        {validDenominations.map((denom) => (
          <label key={denom}>
            <input
              type="checkbox"
              value={denom}
              onChange={handleDenominationChange}
            />
            {denom} 
          </label>
        ))}
      </div>

      <button onClick={handleSubmit}>Submit</button>

      {error && <div style={{ color: "red" }}>{error}</div>}
      {result && <div>{result}</div>}
    </div>
  );
};

export default CoinMinimizer;
