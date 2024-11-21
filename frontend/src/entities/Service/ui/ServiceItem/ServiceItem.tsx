import {IService} from "../../model/IService.ts";

import styles from "./ServiceItem.module.css";

interface IServiceItemProps {
    service: IService;
    deleteService: (id: number) => void;
}

const ServiceItem = ({service, deleteService}: IServiceItemProps) => {
    const { id, name, description, price, duration, category } = service;
    return (
        <div className={styles.item}>
            <div className={styles.wrapper}>
                <h2>{id}. {name}</h2>
                <p>{description}</p>
            </div>
            <p>{price} $</p>
            <p>{duration} min's</p>
            <p>{category}</p>
            <div style={{display: "flex", columnGap: 15}}>
                <button
                    onClick={() => deleteService(id)}
                    className={styles.btnDelete}
                >
                    Delete
                </button>
            </div>
        </div>
    );
};

export default ServiceItem;
