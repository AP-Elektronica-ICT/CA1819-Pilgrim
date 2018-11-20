using System;
using System.Collections.Generic;
using System.Text;

namespace DataLinkLayer.Models
{
    class Pilgrimage
    {
        public int ID { get; set; }


        //10 locations per pilgrimage?
        public Location[] Locations { get; set; }
    }
}
