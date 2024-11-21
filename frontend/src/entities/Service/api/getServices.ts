import axios from "axios";

export const getServices = async () => {
    const response = await axios.get("http://localhost:8080/services");
    return response.data;
}
