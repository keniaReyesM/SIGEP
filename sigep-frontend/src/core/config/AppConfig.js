 const API = {
    HOST: process.env.NODE_ENV === 'production' ? window.location.hostname : window.location.hostname,
    PUERTO: process.env.NODE_ENV === "production" ? ":8080" : ":90",
    PROTOCOLO: process.env.NODE_ENV === "production" ? "http://" : "http://",
    CONTEXTO: process.env.NODE_ENV === "production" ? "/sigep/api/" : "/sigep/api/"
  };
  
  
  export const API_PATH = API.PROTOCOLO.concat(API.HOST,API.PUERTO,API.CONTEXTO);
 
  