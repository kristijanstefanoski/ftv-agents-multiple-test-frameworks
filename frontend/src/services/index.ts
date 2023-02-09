import axios from "axios";
import config from "../config";

const http = axios.create({
  baseURL: config.BASE_URL,
});

export const getGeometricSum = (
  first: number,
  ratio: number,
  count: number
): Promise<{ data: { result: number } }> => {
  return http.get(
    `/sum/geometric?first=${first}&ratio=${ratio}&count=${count}`
  );
};

export const evaluateExpression = (
  expression: string
): Promise<{ data: { result: number } }> => {
  return http.get(`/evaluate/${expression}`);
};
