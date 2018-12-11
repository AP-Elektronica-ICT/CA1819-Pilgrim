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
        public ICollection<Location> Locations { get; set; }
    }
}
