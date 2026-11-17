import React from 'react'
import { useLocation } from 'react-router-dom';



const ImageInfo = ({ }) => {
    console.log("info k pass")

    const location = useLocation();
    const title = location.state?.title;
    const description = location.state?.description;
    const tag = location.state?.tag;
    const category = location.state?.category;
    const thumbnailUrl = location.state?.thumbnailUrl;




    return (
        <div className=' flex justify-center flex-col items-center   '>

            <div >{/* image */}
                <img src={thumbnailUrl} className="m-3 h-100 w-100 bg-cover border-2 bg-center bg-no-repeat" />
                </div>


            <div className='  text-3xl  w-100 flex flex-col gap-1.5 '>
                <label className='   bg-amber-500  rounded-4xl h-15 p-2 shadow-md  text-white'>Title : {title}</label>
                <br></br>


                <label className='bg-amber-500   rounded-4xl h-15 p-2 shadow-md  text-white'>Description : {description}</label>
                <br></br>


                <label className='bg-amber-500   rounded-4xl h-15 p-2 shadow-md  text-white'>Tag : {tag}</label>
                <br></br>


                <label className='bg-amber-500   rounded-4xl h-15 p-2 shadow-md   text-white '>Category : {category}</label>
                <br></br>

            </div>

        </div>
    )
}

export default ImageInfo