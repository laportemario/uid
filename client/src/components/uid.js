import React, { useState, useEffect } from "react";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";
import { makeStyles } from "@material-ui/core/styles";
import { Alert, AlertTitle } from "@material-ui/lab";

const useStyles = makeStyles(theme => ({
  root: {
    width: "100%",
    "& > * + *": {
      marginTop: theme.spacing(2)
    }
  }
}));

const Uid = props => {
  const [postValue, setPostValue] = useState();
  const [getValue, setGetValue] = useState();
  const [error, setError] = useState("");
  const [showError, setShowError] = useState(false);

  const handleChangePostUid = () => {
    (async () => {
      const rawResponse = await fetch("http://localhost:8080/uid", {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: `Bearer ${props.token}`
        }
      });
      const res = await rawResponse.json();

      if (res.error) {
        setError(JSON.stringify(res));
        setShowError(true);
      } else {
        setShowError(false);
        setPostValue(res.id);
      }
    })();
  };

  const handleChangeGetUid = () => {
    (async () => {
      const rawResponse = await fetch("http://localhost:8080/uid", {
        method: "GET",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: `Bearer ${props.token}`
        }
      });
      const res = await rawResponse.json();
      if (res.error) {
        setError(JSON.stringify(res));
        setShowError(true);
      } else {
        setShowError(false);
        setGetValue(res.id);
      }
    })();
  };

  useEffect(() => {}, []);
  const classes = useStyles();

  return (
    <div>
      <center style={{ marginTop: "5vh" }}>
        <h1>UID Generator</h1>
      </center>
      <div className={classes.root} style={{ marginTop: "10vh" }}>
        <Grid
          container
          direction="column"
          spacing={3}
          justify="center"
          alignItems="center"
        >
          <Grid item xs={12}>
            <Grid container direction="column">
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  value={postValue}
                  disabled
                  variant="filled"
                />
              </Grid>
              <Grid item>
                <Button
                  variant="contained"
                  color="primary"
                  onClick={handleChangePostUid}
                  fullWidth
                >
                  Générer un UID
                </Button>
              </Grid>
            </Grid>
          </Grid>
          <Grid item xs={12} style={{ marginTop: "5vh" }}>
            <Grid container direction="column">
              <Grid item>
                <TextField
                  fullWidth
                  value={getValue}
                  disabled
                  variant="filled"
                />
              </Grid>
              <Grid item>
                <Button
                  variant="contained"
                  color="primary"
                  onClick={handleChangeGetUid}
                  fullWidth
                >
                  Récupérer un UID déjà généré
                </Button>
              </Grid>
            </Grid>
          </Grid>
        </Grid>
      </div>
      <Grid
        container
        spacing={0}
        direction="column"
        alignItems="center"
        justify="center"
        style={{ marginTop: "10vh" }}
      >
        <Grid item xs={3}>
          {showError && (
            <div className={classes.root}>
              <Alert severity="error">
                <AlertTitle>Error</AlertTitle>
                {error}
              </Alert>
            </div>
          )}
        </Grid>
      </Grid>
    </div>
  );
};

export default Uid;
