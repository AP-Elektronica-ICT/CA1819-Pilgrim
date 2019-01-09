using System;
using System.Collections.Generic;
using System.Text;

namespace DataLinkLayer.Models
{
    public class Pilgrimage
    {
        public int ID { get; set; }
        public string FireBaseID { get; set; }
        public string username { get; set; }
        public long StartTime { get; set; }
        public int Time { get; set; }
        public ICollection<Location> Locations { get; set; }
    }
}
