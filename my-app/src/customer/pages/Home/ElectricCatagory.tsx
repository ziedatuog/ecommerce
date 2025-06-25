import React from 'react'
import ElectricCatagoryCard from './ElectricaCatagoryCard'

const ElectricCatagory = () => {
  return (
    <div className='flex flex-wrap justify-between py-5 lg:px-20 border-b'> 
     {[1,1,1,1,1,1,1].map((item)=><ElectricCatagoryCard />)}   
    </div>
  )
}

export default ElectricCatagory