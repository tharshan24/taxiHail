import PassengerRides from "../../components/rides/PassengerRides";
import RootRedirect from "../../components/router/RootRedirect";
import DriverRides from "../../components/rides/DriverRides";

const CurrentRidesPage = () => {

    if (sessionStorage.getItem("role") === "PASSENGER") {
        return <PassengerRides/>
    } else if (sessionStorage.getItem("role") === "DRIVER") {
        return <DriverRides/>
    } else {
        return <RootRedirect/>
    }
}

export default CurrentRidesPage;