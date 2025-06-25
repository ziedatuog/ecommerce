import React from 'react'
import ShopByCatagoryCard from './ShopByCatagoryCard'

const ShopByCatagory = () => {
  return (
    <div className='flex flex-wrap justify-between lg:px-20 gap-7'> 
        {[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1].map((item)=>
        <ShopByCatagoryCard />)}
    </div>
  )
}

export default ShopByCatagory