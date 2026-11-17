import axios from 'axios';
import 'remixicon/fonts/remixicon.css'
import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';

const SearchTab = () => {
  const [inputValue, setInputValue] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    setInputValue('');
  }, [location.pathname]);



  const handleSearch = () => {
    if (!inputValue.trim()) return
    navigate('/searchResponsePage', { state: { keyword: inputValue } });
    console.log("input value : " + inputValue)
  }

  return (

    <form className='h-14 w-3xl   p-2  border-2 rounded-4xl  flex 
    flex-row place-items-center   justify-between '
      onChange={handleSearch}
    >
      <div className='text-4xl'> <i className="ri-search-line"></i>   </div>

      <div className='  grow'>
        <input
          type='text '
          placeholder='Quick Search Image '
          value={inputValue}
          onChange={(e) => {
            setInputValue(e.target.value);
          }} />

      </div>

      <button
        className='h-11 w-28 bg-amber-500 text-amber-50 rounded-4xl shadow-md active:shadow-inner  '
      >
        Search
      </button>


    </form>


  )
}

export default SearchTab