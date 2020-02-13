import React, { useState, useEffect } from "react";
import Uid from "./components/uid";

const App = props => {
  const [token, setToken] = useState();

  useEffect(() => {
    (async () => {
      const rawResponse = await fetch("http://localhost:8080/authenticate", {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ username: "mario", password: "metroscope" })
      });
      const res = await rawResponse.json();

      setToken(res.token);
    })();
  }, []);

  return <Uid token={token} />;
};

export default App;
