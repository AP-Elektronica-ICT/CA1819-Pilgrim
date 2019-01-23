using System;
using System.Collections.Generic;
using System.Text;

namespace DataLinkLayer.Models
{
    public class Collection
    {
        public int ID { get; set; }
        public string FireBaseID { get; set; }
        

        public ICollection<Location> Locations { get; set; }

        [Newtonsoft.Json.JsonIgnore]
        public ICollection<CollectionLocation> CollectionLocations { get; set; }


    }
}
