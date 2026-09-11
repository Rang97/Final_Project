import { useCallback, useEffect, useState } from "react";
import {
  getSaju,
  calculateSaju,
  saveSajuInput,
  getBirthTimeOptions,
} from "../api/sajuApi";

export function useSajuSummary() {
  const [saju, setSaju] = useState(null);
  const [birthTimeOptions, setBirthTimeOptions] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isSavingInput, setIsSavingInput] = useState(false);
  const [isCalculating, setIsCalculating] = useState(false);

  const fetchSaju = useCallback(async () => {
    setIsLoading(true);
    setError(null);
    try {
      const data = await getSaju();
      setSaju(data);
    } catch (err) {
      if (err.response?.status === 404) {
        setSaju(null);
      } else {
        setError(err);
      }
    } finally {
      setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchSaju();
    getBirthTimeOptions()
      .then(setBirthTimeOptions)
      .catch(() => setBirthTimeOptions([]));
  }, [fetchSaju]);

  const saveInput = async (body) => {
    setIsSavingInput(true);
    try {
      await saveSajuInput(body);
    } finally {
      setIsSavingInput(false);
    }
  };

  const calculate = async () => {
    setIsCalculating(true);
    setError(null);
    try {
      const data = await calculateSaju();
      setSaju(data);
      return data;
    } catch (err) {
      setError(err);
      throw err;
    } finally {
      setIsCalculating(false);
    }
  };

  return {
    saju,
    birthTimeOptions,
    isLoading,
    error,
    isSavingInput,
    isCalculating,
    saveInput,
    calculate,
    refetch: fetchSaju,
  };
}
