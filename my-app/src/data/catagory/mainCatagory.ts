export const mainCatagory = [
    {
        name:"Men",
        catagoryId:"men",
        level:1,
        levelTwoCatagory:[
            {
                "name":"Topwere",
                "catagoryId":"men-topwere",
                "parentCatagoryId":"men",
                "level":2,
            },
            {
                "name":"Bottomwere",
                "catagoryId":"men-bottomwere",
                "parentCatagoryId":"men",
                "level":2,
            } 
        ]
    },
    name:"Women",
    catagoryId:"women",
    level:1,
    levelTwoCatagory:[
        {
            "parentCatagoryId":"women",
            "level":2,
            "name":"Ethiopian & Fusion Wear",
            "catagoryId":"women-ethiopian-fusion-wear",
        },
        {
            "parentCatagoryId":"women",
            "level":2,
            "name":"Western Wear",
            "catagoryId":"women-western-wear",
        } 
    ]
]