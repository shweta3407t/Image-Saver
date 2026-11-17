import React from 'react'
import SearchTab from './SearchTab'
import { useNavigate } from 'react-router-dom'



//react router dom
//axios

const Nevbar = () => {


  const navigate = useNavigate();



  return (
    <div className=' fixed  h-22 w-full flex justify-between p-8 border-b-2  bg-white place-items-center   shadow-md '>
      <div className='flex items-center  gap-10  '>
        <button className='text-3xl '><i className="ri-arrow-left-long-fill" 
        onClick={()=>{
          navigate('/')
        }}></i></button>
        <div className=' bg-black h-11  w-11  text-amber-50 border-radi rounded-full font-semibold '>logo</div>
      </div>

      <SearchTab />

      <button className=' bg-black h-11  w-72  text-amber-50 border-radi rounded-4xl  
        font-semibold   items-center '
        onClick={() => {

          navigate('/post')

        }} >  Post Image
      </button>



    </div>



  )
}

export default Nevbar