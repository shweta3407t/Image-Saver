import React from 'react'
import Nevbar from './components/Nevbar'
import { Route, Routes } from 'react-router-dom'
import Post from './pages/Post'
import Home from './pages/Home'
import SearchResponsePage from './pages/SearchResponsePage'
import ImageInfo from './pages/ImageInfo'


const App = () => {
   
  return (
    <div >

      <Nevbar />

      <main className="pt-22 ">
        <Routes>
          <Route path='/' element={<Home />} />
          <Route path='/post' element={<Post />} />
          <Route path='/searchResponsePage' element={<SearchResponsePage />} />
          <Route path='/imageInfo' element={<ImageInfo />} />
          
        </Routes>

      </main>


    </div>
  )
}

export default App