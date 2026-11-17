import React, { useEffect, useState } from 'react';
import Images from '../components/Images';
import axios from 'axios';

const DisplayImages = () => {
    const size = 20;

    // 1. Initialize as numbers (0), not arrays ([0])
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const [hasPrevious, setHasPrevious] = useState(false);
    const [hasNext, setHasNext] = useState(false);

    const [isLoading, setIsLoading] = useState(true);
    const [data, setData] = useState([]);

    useEffect(() => {
 
        const displayImg = async (page) => {
            setIsLoading(true);
            try {
                const url = `http://localhost:8080/api/image/search`;
                const response = await axios.get(url, {
                    params: {
                        page: page,
                        size: size
                    },
                });

                const apiResult = response.data;
                setData(apiResult.content);

                setTotalPages(apiResult.totalPages);
                setHasPrevious(apiResult.hasPrevious);
                setHasNext(apiResult.hasNext);
            } catch (error) {
                console.error("Error fetching data:", error);

            } finally {
                setIsLoading(false);
            }
        };

        displayImg(currentPage);

    }, [currentPage]);

    // 3. Fixed implicit return in setCurrentPage
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

    if (isLoading) return <p className='text-blue-700 text-5xl'>Loading. Image ..</p>;

    return (
        <div>
            {/* image div */}
            <div className='flex flex-wrap mb-16'>
                {data.map((singleData) => (
                    <Images
                        key={singleData.id}
                        id={singleData.id}
                        title={singleData.title}
                        description={singleData.description}
                        tag={singleData.tag}
                        category={singleData.category}
                       
                        originalUrl={singleData.originalUrl}
                        thumbnailUrl={singleData.thumbnailUrl}
                    />
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
    );
};

export default DisplayImages;
