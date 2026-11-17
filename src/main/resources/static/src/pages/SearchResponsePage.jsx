import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom';
import Images from '../components/Images';


const SearchResponsePage = () => {
  const location = useLocation();
  const keyword = location.state?.keyword;



  console.log("search reaponse page k pass")
  console.log("keyword : " + keyword)


  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [hasPrevious, setHasPrevious] = useState(false)
  const [hasNext, setHasNext] = useState(false)

  const [isLoading, setIsLoading] = useState(true);
  const [data, setData] = useState([])

  const size = 20

  useEffect(() => {
    setCurrentPage(0);
    console.log("111 use effect")
  }, [keyword]);
  


  useEffect(() => {
    console.log("222222222")


    const searchImage = async (page) => {

      if (!keyword || keyword === "") {
        setData([]);
        console.log("in if else")
        setTotalPages(1);
        setHasPrevious(false);
        setHasNext(false);
        return  
      }


      const url = `http://localhost:8080/api/image/search/keyword`;
      setIsLoading(true);

      try {
        const searchResponse = await axios.get(url, {
          params: {
            keyword: keyword,
            page: page,
            size: size
          }
        });

        console.log("s reaponse " + searchResponse)
        const apiResult = searchResponse.data;

        // setIsLoading(false);

        setData(apiResult.content);
        setTotalPages(apiResult.totalPages);
        setHasPrevious(Boolean(apiResult.hasPrevious));
        setHasNext(Boolean(apiResult.hasNext));

      } catch (error) {
        console.error("Error fetching data:", error);
      } finally {
        setIsLoading(false);
      }
    }

    searchImage(currentPage);
  }, [currentPage, keyword])

  //pagination
  const handleNextPage = () => {
    if (currentPage < totalPages - 1 || hasNext) {
      setCurrentPage((prev) => prev + 1);
    }
  };

  const handlePrevPage = () => {
    if (currentPage > 0 || hasPrevious) {
      setCurrentPage((prev) => prev - 1);
    }
  };

  const handlePageClick = (pageNumber) => {
    setCurrentPage(pageNumber - 1);
  };

  const pages = Array.from({ length: totalPages }, (_, i) => i + 1);

  // if (isLoading) return <p className='text-blue-700 text-5xl'>Loading. Image ..</p>;

  return (
    <div  >
      {/* //content div */}
      <div className='  text-6xl flex flex-wrap mb-16 '>
        {(data.map((singleData) =>
          <Images
            key={singleData.id}
            id={singleData.id}
            title={singleData.title}
            description={singleData.description}
            tag={singleData.tag}
            category={singleData.category}
         
            originalUrl={singleData.originalUrl}
            thumbnailUrl={singleData.thumbnailUrl} />
        ))}

      </div>

      {/* pagination div */}
      <div className='bg-mist-50 fixed  bottom-0 left-0 w-full h-16 flex items-center justify-center gap-4 z-40 shadow-inner'>
        {/* prev button */}
        <button
          onClick={handlePrevPage}
          disabled={!hasPrevious || isLoading}
          className='bg-amber-500 h-11 w-72 text-amber-50 rounded-4xl font-semibold items-center disabled:opacity-50'
        >
          <i className="ri-arrow-left-double-fill"></i> Previous
        </button>

        {/* page buttons */}
        {pages.map((number) => (
          <button
            key={number}
            onClick={() => handlePageClick(number)}
            // 4. Fixed template literal syntax (${...}) and 0-indexed comparison
            className={`${currentPage === number - 1 ? 'bg-amber-700 ring-2 ring-amber-900' : 'bg-amber-500'} h-11 w-11 text-amber-50 rounded-full font-semibold items-center`}
            disabled={isLoading}
          >
            {number}
          </button>
        ))}

        {/* next button */}
        <button
          onClick={handleNextPage}
          disabled={!hasNext || isLoading}
          className='bg-amber-500 h-11 w-72 text-amber-50 rounded-4xl font-semibold items-center disabled:opacity-50'
        >
          Next <i className="ri-arrow-right-double-fill"></i>
        </button>
      </div>
    </div>
  )
}

export default SearchResponsePage



