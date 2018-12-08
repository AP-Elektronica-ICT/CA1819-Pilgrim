using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace DataLinkLayer.Models
{
    public class Pilgrimage
    {
        public int ID { get; set; }

        public long StartTime { get; set; }

        public int Time { get; set; }

        //10 locations per pilgrimage?
       // public Location[] Locations { get; set; }

        public ICollection<Location> Locations { get; set; }
    }
}
