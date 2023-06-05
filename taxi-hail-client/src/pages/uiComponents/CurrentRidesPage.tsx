import PassengerRides from "../../components/rides/PassengerRides";
import RootRedirect from "../../components/router/RootRedirect";

const CurrentRidesPage = () => {

    if (sessionStorage.getItem("role") === "PASSENGER") {
        return <PassengerRides />
    }
    else if (sessionStorage.getItem("role") === "DRIVER") {
        return <PassengerRides />
    }
    else {
        return <RootRedirect />
    }
}

export default CurrentRidesPage;