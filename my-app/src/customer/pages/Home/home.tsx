import React from 'react'
import ElectricCatagory from './ElectricCatagory'
import CategoryGrid from './CategoryGrid/CategoryGrid'
import Deal from './Deal/Deal'
import ShopByCatagory from './ShopeByCatagory/ShopByCatagory'
import { Storefront } from '@mui/icons-material'
import { Button } from '@mui/material'

const home = () => {
    return (
        <>
            <div className='space-y-5 lg:space-y-10 relative'>
                <ElectricCatagory />
                <CategoryGrid />

                <div className='pt-20'>
                    <h1 className='text-lg lg:text-4xl font-bold text-primary-color pb-5 
                    lg:pb-10 text-center'>TODAY'S DEAL</h1>
                    <Deal />

                </div>

                <section className='py-20'>
                    <h1 className='text-lg lg:text-4xl font-bold text-primary-color pb-5 
                    lg:pb-10 text-center'>SHOP BY CATEGORY</h1>
                    <ShopByCatagory />
                </section>

                <section className='lg:px-20 relative h-[200px] lg:h-[450px] object-cover'>
                    {/* <img className='w-full h-full' src="https://zosh-bazzar-zosh.vercel.app/
                    seller_banner_image.jpg" alt="" /> */}
                      <img className='w-full h-full' src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSwiVqpNd0zv349lznWpZI0-KKoEyp-sFiA_g&s" alt="" />
                    <div className='absolute top-1/2 
                    left-4 lg:left-[15rem] transform-translate-y-1/2 font-semibold lg:text-4xl space-y-3'>
                        <h1>Sell your Product</h1>
                        <p className='text-lg md:text-2xl'>with  <strong className='logo'>Andinet Bazzar</strong></p>
                       
                       <div className='pt-6 flex justify-center'>
                        <Button startIcon={<Storefront />} variant='contained' size='large'>
                            Become Seller
                        </Button>
                       </div>

                    </div>
                </section>

            </div>
        </>
    )
}

export default home