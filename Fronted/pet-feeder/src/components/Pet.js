import './Pet.css';


const Pet = ({pet}) => {

    console.log(JSON.stringify(pet))

      return (
        <div className="pet-container">
          {pet.name}
        </div>
      );
    };
    
    export default Pet;